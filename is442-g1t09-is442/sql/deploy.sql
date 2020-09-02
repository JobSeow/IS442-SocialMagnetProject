drop database if exists magnet;
create database magnet;

-- use database--
use magnet;

-- create member table ---
drop table if exists magnet.member;
CREATE TABLE magnet.member (
--    memberID INT NOT NULL auto_increment,
	username varchar(128),
    fullname varchar(128),
    password varchar(128),
    PRIMARY KEY(username)
);

-- create Friends table ----
drop table if exists magnet.friends;
CREATE TABLE magnet.friends (
    username varchar(128) not null REFERENCES magnet.member(username),
    friend_username varchar(128) not null REFERENCES magnet.member(username),
    PRIMARY KEY (username, friend_username),
    FOREIGN KEY(username) REFERENCES magnet.member(username),
    FOREIGN KEY(friend_username) REFERENCES magnet.member(username)
);

-- create post table
 drop table if exists magnet.post;
 CREATE TABLE magnet.post (
     postID varchar(128),
     posterUsername varchar(128) references magnet.member(username),
     receiverUsername varchar(128) references magnet.member(username),
     datePosted DATETIME,
     message varchar(128) not null,
     likes INT,
     dislikes INT,
     PRIMARY KEY (postID),
     FOREIGN KEY(posterUsername) REFERENCES magnet.member(username),
     FOREIGN KEY(receiverUsername) REFERENCES magnet.member(username)
 );

-- create reply table
drop table if exists magnet.reply;
 CREATE TABLE magnet.reply (
     replyID varchar(128),
     postedToID varchar(128),
     posterUsername varchar(128) ,
     receiverUsername varchar(128) ,
     datePosted DATETIME,
     message varchar(128) not null,
     PRIMARY KEY (replyID),
     FOREIGN KEY(posterUsername) REFERENCES magnet.post(postID)
 );

-- likes table
drop table if exists magnet.likes;
 CREATE TABLE magnet.likes (
     postID varchar(128),
     username varchar(128),
     PRIMARY KEY (postID,username),
     FOREIGN KEY(postID) REFERENCES magnet.post(postID)
 );

 -- dislikes table
drop table if exists magnet.dislikes;
 CREATE TABLE magnet.dislikes (
     postID varchar(128),
     username varchar(128),
     PRIMARY KEY (postID,username),
     FOREIGN KEY(postID) REFERENCES magnet.post(postID)
 );

 -- create friend requests table
 drop table if exists magnet.friendRequests;
 CREATE TABLE magnet.friendRequests (
     sender varchar(128) references magnet.member(username),
     receiver varchar(128) references magnet.member(username),
     PRIMARY KEY (sender,receiver),
     FOREIGN KEY(sender) REFERENCES magnet.member(username),
     FOREIGN KEY(receiver) REFERENCES magnet.member(username)
 );

-- create farmer table
drop table if exists magnet.farmer;
CREATE TABLE magnet.farmer(
    ownerUsername  VARCHAR(128)references magnet.member(username),
    gold  INT NOT NULL,
    xp INT NOT NULL,
    rankName VARCHAR(128),
    plots INT NOT NULL,
    giftsSent INT NOT NULL,
    PRIMARY KEY(ownerUsername),
    FOREIGN KEY(ownerUsername) REFERENCES magnet.member(username)
);

-- create inventory table
drop table if exists magnet.inventory;
CREATE TABLE magnet.inventory(

    username  VARCHAR(128)references magnet.farmer(ownerUsername),
    cropName VARCHAR(128),
    quantity INT NOT NULL,
    PRIMARY KEY(username,cropName),
    FOREIGN KEY(username) REFERENCES magnet.farmer(ownerUsername)
);

-- create land table
drop table if exists magnet.land;
CREATE TABLE magnet.land(
    owner VARCHAR(128)references magnet.farmer(ownerUsername),
    plotNumber INT,
    plantTime DATETIME,
    finishedTime DATETIME,
    witherTime DATETIME,
    cropName VARCHAR(128),
    currentStatus INT,
    progressBar VARCHAR(128),
    PRIMARY KEY(owner,plotNumber),
    FOREIGN KEY(owner) REFERENCES magnet.farmer(ownerUsername)
);

-- test cases

INSERT INTO magnet.member(username, fullname,password)
VALUES('job', 'Seow Jian Liang Job', 'password'),
('tlz', 'Tan Li Zhen', 'password'),
('tifftan', 'Tiffany Tan', 'password'),
('daryl', 'Daryl Ang Jun Hao', 'password');

INSERT INTO magnet.farmer(ownerUsername, gold, xp, rankName, plots, giftsSent)
VALUES('job', 99999 , 99999, 'Legendary', 9, 0),
('daryl', 99999 , 99999,'Legendary', 9, 0),
('tlz', 999 , 999,'Novice', 5, 0),
('tifftan', 1999 , 1999,'Novice', 5, 0);

INSERT INTO magnet.friends(username, friend_username)
VALUES('job', 'daryl'),
('daryl', 'job'),
('job','tlz'),
('tlz', 'job'),
('daryl','tlz'),
('tlz','daryl');

INSERT INTO magnet.inventory(username, cropName, quantity)
VALUES('daryl', 'Papaya', 9999),
('daryl','Pumpkin', 9999),
('daryl','Sunflower', 9999),
('job','Papaya', 9999),
('job','Pumpkin', 9999),
('job','Sunflower', 9999),
('job','Watermelon', 9999);