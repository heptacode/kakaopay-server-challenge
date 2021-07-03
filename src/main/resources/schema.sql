DROP TABLE IF EXISTS memberships;

CREATE TABLE memberships (
  seq INT AUTO_INCREMENT PRIMARY KEY,
  membershipId VARCHAR(250) NOT NULL,
  userId VARCHAR(250) NOT NULL,
  membershipName VARCHAR(250) NOT NULL,
  startDate VARCHAR(250) NOT NULL,
  membershipStatus VARCHAR(250) NOT NULL,
  point INT NOT NULL
);