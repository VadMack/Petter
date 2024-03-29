#!/bin/bash
echo "Sleeping for 5 seconds"
sleep 5

echo mongo_setup.sh time now: `date +"%T" `
mongosh --host mongo:27017 <<EOF
  var cfg = {
    "_id": "rs0",
    "version": 1,
    "members": [
      {
        "_id": 0,
        "host": "mongo:27017",
        "priority": 2
      }
    ]
  };
  rs.initiate(cfg);
EOF
echo "User initialization is starting"
mongosh --host mongo:27017 < /scripts/init-user.js
echo "User initialized successfully"
