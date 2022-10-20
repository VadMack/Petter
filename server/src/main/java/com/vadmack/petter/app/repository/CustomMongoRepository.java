package com.vadmack.petter.app.repository;

import com.mongodb.client.result.UpdateResult;
import com.vadmack.petter.app.exception.NotFoundException;
import com.vadmack.petter.app.exception.ServerSideException;
import com.vadmack.petter.app.model.ModelUpdateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

public abstract class CustomMongoRepository {
  protected MongoTemplate mongoTemplate;

  @Autowired
  public final void setMongoTemplate(MongoTemplate mongoTemplate) {
    this.mongoTemplate = mongoTemplate;
  }

  protected final <T> void updateById(ModelUpdateDto dto, String id,
                                      Class<T> madelClass) {
    Criteria criteria = Criteria.where("_id").is(id);
    Update update = new Update();
    String entityName = madelClass.getSimpleName();

    try {
      BeanInfo beanInfo = Introspector.getBeanInfo(dto.getClass());
      for (PropertyDescriptor propertyDesc : beanInfo.getPropertyDescriptors()) {
        String propertyName = propertyDesc.getName();
        Method getter = propertyDesc.getReadMethod();
        Object value = getter.invoke(dto);

        if (value != null && !propertyName.equals("class")) {
          update.set(propertyName, value);
        }
      }
    } catch (Exception ex) {
      throw new ServerSideException(String.format("Failed to update %s with id=%s",
              entityName, id));
    }

    UpdateResult result = mongoTemplate.updateFirst(Query.query(criteria), update, madelClass);
    if (result.getMatchedCount() < 1) {
      throw new NotFoundException(String.format("%s with id=%s not found", entityName, id));
    }
  }
}
