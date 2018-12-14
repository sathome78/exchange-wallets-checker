use wallet_checker;

-- auto-generated definition
DROP TABLE IF EXISTS COIN_LOGS;
create table COIN_LOGS
(
  COIN_ID           int auto_increment PRIMARY KEY ,
  AMOUNT_BEFORE     double         default 0                 null,
  AMOUNT_AFTER      double         default 0                 null,
  AMOUNT_USD_BEFORE decimal(20, 2) default 0.00              null,
  AMOUNT_USD_AFTER  decimal(20, 2) default 0.00              null,
  LAST_UPDATED_TIME datetime       default CURRENT_TIMESTAMP null,
  FOREIGN KEY (COIN_ID)
    REFERENCES COIN(ID)
) ENGINE=INNODB;



drop trigger if exists COIN_LOGGER;
create trigger COIN_LOGGER
  before UPDATE
  on COIN
  for each row
BEGIN
  IF (SELECT COUNT(1) FROM COIN_LOGS WHERE COIN_ID = OLD.ID) = 0 THEN
    INSERT INTO COIN_LOGS(COIN_ID, AMOUNT_BEFORE, AMOUNT_AFTER, AMOUNT_USD_BEFORE, AMOUNT_USD_AFTER) values (OLD.ID, OLD.CURRENT_AMOUNT, NEW.CURRENT_AMOUNT, OLD.AMOUNT_IN_USD, NEW.AMOUNT_IN_USD);
  ELSE
    UPDATE COIN_LOGS SET AMOUNT_BEFORE = OLD.CURRENT_AMOUNT,AMOUNT_AFTER = NEW.CURRENT_AMOUNT, AMOUNT_USD_BEFORE = OLD.AMOUNT_IN_USD, AMOUNT_USD_AFTER = NEW.AMOUNT_IN_USD, LAST_UPDATED_TIME = now() WHERE COIN_ID = OLD.ID;
  END IF; END;


delimiter ;
