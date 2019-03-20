CREATE TABLE agent_data_src (
  id SERIAL,
  qa_data VARCHAR(255),
  testrun INT,
  stamp BIGINT,
  PRIMARY KEY (id)
);


CREATE TABLE agent_notifications (
  id SERIAL,
  notification VARCHAR(255),
  PRIMARY KEY (id)
);