package com.vadmack.petter.app.repository;

import com.mongodb.client.result.UpdateResult;
import com.vadmack.petter.app.exception.NotFoundException;
import com.vadmack.petter.app.exception.ServerSideException;
import com.vadmack.petter.app.model.ModelFilter;
import com.vadmack.petter.app.model.ModelUpdateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

public abstract class CustomMongoRepository {
  protected MongoTemplate mongoTemplate;

  @Autowired
  public final void setMongoTemplate(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  protected final <T> List<T> findByProperties(ModelFilter filter, Pageable page, Class<T> modelClass) {
    final Query query = new Query().with(page);
    final List<Criteria> criteria = new ArrayList<>();

    handleBean(filter,
            (String propertyName, Object value) -> {
      if (propertyName.startsWith("min")) {
        criteria.add(Criteria.where(propertyName.substring(3)).gte(value));
      } else if (propertyName.startsWith("max")) {
        criteria.add(Criteria.where(propertyName.substring(3)).lte(value));
      } else if (propertyName.startsWith("not")) {
        criteria.add(Criteria.where(propertyName.substring(3)).ne(value));
      } else {
        criteria.add(Criteria.where(propertyName).is(value));
      } },
            "An error occurred during findByProperties() method", true);

    if (!criteria.isEmpty())
      query.addCriteria(new Criteria().andOperator(criteria.toArray(new Criteria[0])));
    return mongoTemplate.find(query, modelClass);

  }

  protected final <T> void updateById(ModelUpdateDto dto, String id,
                                      Class<T> modelClass) {
    Criteria criteria = Criteria.where("_id").is(id);
    Update update = new Update();
    String entityName = modelClass.getSimpleName();

    handleBean(dto, update::set, String.format("Failed to update %s with id=%s", entityName, id), false);

    UpdateResult result = mongoTemplate.updateFirst(Query.query(criteria), update, modelClass);
    if (result.getMatchedCount() < 1) {
      throw new NotFoundException(String.format("%s with id=%s not found", entityName, id));
    }
  }

  private void handleBean(Object bean, BiConsumer<String, Object> task, String errorMsg, boolean ignoreNull) {
    try {
      BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
      for (PropertyDescriptor propertyDesc : beanInfo.getPropertyDescriptors()) {
        String propertyName = propertyDesc.getName();
        Method getter = propertyDesc.getReadMethod();
        Object value = getter.invoke(bean);

        if (!propertyName.equals("class")) {
          if (ignoreNull && value == null){
            continue;
          }
          task.accept(propertyName, value);
        }
      }
    } catch (Exception ex) {
      throw new ServerSideException(errorMsg);
    }
  }

}
