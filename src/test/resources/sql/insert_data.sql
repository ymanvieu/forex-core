insert into symbols (code,name,country_flag,currency) values 
('EUR','Euro','europeanunion',null),
('USD','US Dollar','us',null),
('GBP','British Pound Sterling','gb',null),
('BRE',null,null,null),
('XAU','Gold','gold',null),
('XAG','Silver','silver',null),
('XAF','CFA Franc BEAC',null,null),
('UBI.PA','Ubisoft Entertainment SA',null,'EUR');


INSERT INTO LATESTRATES(DATE, FROMCUR, TOCUR, VALUE) VALUES (TIMESTAMP '2015-01-30 13:55:00.0', 'USD', 'EUR', 0.88);
INSERT INTO LATESTRATES(DATE, FROMCUR, TOCUR, VALUE) VALUES (TIMESTAMP '2015-04-06 02:00:00.0', 'BRE', 'USD', 55.18);


INSERT INTO RATES(DATE, FROMCUR, TOCUR, VALUE) VALUES
(TIMESTAMP '2015-01-30 22:47:39.0', 'USD', 'XAG', 0.0581670000),
(TIMESTAMP '2015-02-01 22:42:10.0', 'USD', 'XAF', 580.5191650000),
(TIMESTAMP '2015-02-01 22:42:10.0', 'USD', 'EUR', 0.883353),
(TIMESTAMP '2015-02-01 22:42:10.0', 'USD', 'USD', 1.0000000000),

(TIMESTAMP '2015-02-02 08:41:00.0', 'USD', 'XAF', 579.6465450000),
(TIMESTAMP '2015-02-02 08:42:50.0', 'USD', 'EUR', 0.8820440000),
(TIMESTAMP '2015-02-02 08:41:00.0', 'USD', 'USD', 1.0000000000),
(TIMESTAMP '2015-02-02 08:42:50.0', 'USD', 'GBP', 0.6649820000),
(TIMESTAMP '2015-02-02 08:41:55.0', 'USD', 'XAU', 0.0007830000),
(TIMESTAMP '2015-02-27 02:00:00.0', 'BRE', 'USD', 60.75),
(TIMESTAMP '2015-04-06 02:00:00.0', 'BRE', 'USD', 55.18);