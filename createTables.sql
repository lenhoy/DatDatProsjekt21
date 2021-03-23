CREATE TABLE User_ (
	EMail VARCHAR(30) NOT NULL, 
    Name_ VARCHAR(30),  
    Password_ VARCHAR(30),
	CONSTRAINT User_PK PRIMARY KEY (EMail)
);

CREATE TABLE Student (
	EMail VARCHAR(30) NOT NULL, 
    AnonAlias VARCHAR(30),
    CONSTRAINT Student_PK PRIMARY KEY (EMail), 
    CONSTRAINT Student_FK FOREIGN KEY (EMail) REFERENCES User_(EMail)
		ON UPDATE CASCADE
        ON DELETE CASCADE
);

CREATE TABLE Instructor (
	EMail VARCHAR(30) NOT NULL, 
    CONSTRAINT Instructor_PK PRIMARY KEY (EMail), 
    CONSTRAINT Instructor_FK FOREIGN KEY (EMail) REFERENCES User_(EMail)
		ON UPDATE CASCADE
        ON DELETE CASCADE
);

CREATE TABLE Course (
	CourseID INTEGER NOT NULL, 
    Name_ VARCHAR(30), 
    Term VARCHAR(30), 
    Anonymous BOOLEAN NOT NULL, 
    CONSTRAINT Course_PK PRIMARY KEY (CourseID)
);

CREATE TABLE Folder (
	Name_ VARCHAR(30) NOT NULL, 
    CourseID INTEGER NOT NULL, 
    CONSTRAINT Folder_PK PRIMARY KEY (Name_, CourseID), 
    CONSTRAINT Folder_FK FOREIGN KEY (CourseID) REFERENCES Course(CourseID)
		ON UPDATE CASCADE
        ON DELETE CASCADE
);

CREATE TABLE Post (
	PostID INTEGER NOT NULL, 
    content VARCHAR(500), 
    CONSTRAINT Post_PK PRIMARY KEY (PostID)
);

CREATE TABLE Thread (
	PostID INTEGER NOT NULL,
    Tag VARCHAR(50), 
    Header VARCHAR(50), 
	CONSTRAINT Thread_PK PRIMARY KEY (PostID), 
    CONSTRAINT Thread_FK FOREIGN KEY (PostID) REFERENCES Post(PostID)
		ON UPDATE CASCADE
        ON DELETE CASCADE
);

CREATE TABLE FollowUp (
	PostID INTEGER NOT NULL,
    CONSTRAINT FollowUp_PK PRIMARY KEY (PostID), 
    CONSTRAINT FollowUp_FK FOREIGN KEY (PostID) REFERENCES Post(PostID)
    	ON UPDATE CASCADE
        ON DELETE CASCADE
);

CREATE TABLE Reply (
	PostID INTEGER NOT NULL,
    CONSTRAINT Reply_PK PRIMARY KEY (PostID), 
    CONSTRAINT Reply_FK FOREIGN KEY (PostID) REFERENCES Post(PostID)
    	ON UPDATE CASCADE
        ON DELETE CASCADE
);


CREATE TABLE StudentInCourse (
	EMail VARCHAR(30) NOT NULL,
	CourseID INTEGER NOT NULL,
    CONSTRAINT StudentInCourse_PK PRIMARY KEY (Email, CourseID), 
    CONSTRAINT StudentInCourse_FK1 FOREIGN KEY (EMail) REFERENCES Student(EMail)
    	ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT StudentInCourse_FK2 FOREIGN KEY (CourseID) REFERENCES Course(CourseID)
    	ON UPDATE CASCADE
        ON DELETE CASCADE
);

CREATE TABLE ManagedBy (
	EMail VARCHAR(30) NOT NULL,
	CourseID INTEGER NOT NULL,
    CONSTRAINT ManagedBy_PK PRIMARY KEY (Email, CourseID), 
    CONSTRAINT ManagedBy_FK1 FOREIGN KEY (EMail) REFERENCES Instructor(EMail)
    	ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT ManagedBy_FK2 FOREIGN KEY (CourseID) REFERENCES Course(CourseID)
    	ON UPDATE CASCADE
        ON DELETE CASCADE
);

CREATE TABLE SubFolder (
	SuperFolderName VARCHAR(30) NOT NULL, 
    SuperCourseID INTEGER NOT NULL, 
    SubFolderName VARCHAR(30) NOT NULL, 
    SubCourseID INTEGER NOT NULL,
    CONSTRAINT SubFolder_PK PRIMARY KEY (SuperFolderName, SuperCourseID, SubFolderName), 
    CONSTRAINT SubFolder_FK1 FOREIGN KEY (SuperFolderName) REFERENCES Folder(Name_)
    	ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT SubFolder_FK2 FOREIGN KEY (SuperCourseID) REFERENCES Folder(CourseID)
    	ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT SubFolder_FK3 FOREIGN KEY (SubFolderName) REFERENCES Folder(Name_)
    	ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT SubFolder_FK4 FOREIGN KEY (SubCourseID) REFERENCES Folder(CourseID)
    	ON UPDATE CASCADE
        ON DELETE CASCADE
);

CREATE TABLE ThreadInFolder (
	CourseID INTEGER NOT NULL, 
    Name_ VARCHAR(30) NOT NULL, 
    PostID INTEGER NOT NULL, 
    CONSTRAINT ThreadInFolder_PK PRIMARY KEY (CourseID, Name_, PostID), 
    CONSTRAINT ThreadInFolder_FK1 FOREIGN KEY (CourseID) REFERENCES Folder(CourseID)
    	ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT ThreadInFolder_FK2 FOREIGN KEY (Name_) REFERENCES Folder(Name_)
    	ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT ThreadInFolder_FK3 FOREIGN KEY (PostID) REFERENCES Thread(PostID)
    	ON UPDATE CASCADE
        ON DELETE CASCADE
);

CREATE TABLE FollowUpInThread (
	FollowUpID INTEGER NOT NULL,
    ThreadID INTEGER NOT NULL, 
	CONSTRAINT FollowUpInThread_PK PRIMARY KEY (FollowUpID, ThreadID), 
    CONSTRAINT FollowUpInThread_FK1 FOREIGN KEY (FollowUpID) REFERENCES FollowUp(PostID)
    	ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT FollowUpInThread_FK2 FOREIGN KEY (ThreadID) REFERENCES Thread(PostID)
    	ON UPDATE CASCADE
        ON DELETE CASCADE
);

CREATE TABLE ReplyInFollowUp (
	FollowUpID INTEGER NOT NULL,
    ReplyID INTEGER NOT NULL, 
	CONSTRAINT ReplyInFollowUp_PK PRIMARY KEY (FollowUpID, ReplyID), 
    CONSTRAINT ReplyInFollowUp_FK1 FOREIGN KEY (FollowUpID) REFERENCES FollowUp(PostID)
    	ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT ReplyInFollowUp_FK2 FOREIGN KEY (ReplyID) REFERENCES Reply(PostID)
    	ON UPDATE CASCADE
        ON DELETE CASCADE
);

CREATE TABLE Linked (
	PostID INTEGER NOT NULL, 
    LinkedID INTEGER NOT NULL, 
    CONSTRAINT Linked_PK PRIMARY KEY (PostID, LinkedID), 
    CONSTRAINT Linked_FK1 FOREIGN KEY (PostID) REFERENCES Post(PostID)
    	ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT Linked_FK2 FOREIGN KEY (LinkedID) REFERENCES Post(PostID)
    	ON UPDATE CASCADE
        ON DELETE CASCADE
);

CREATE TABLE PostedBy (
	EMail VARCHAR(30) NOT NULL, 
    PostID INTEGER NOT NULL, 
    Date_ DATETIME, 
    CONSTRAINT PostedBy_PK PRIMARY KEY (EMail, PostID), 
    CONSTRAINT PostedBy_FK1 FOREIGN KEY (Email) REFERENCES User_(EMail)
    	ON UPDATE CASCADE
        ON DELETE NO ACTION,
    CONSTRAINT PostedBy_FK2 FOREIGN KEY (PostID) REFERENCES Post(PostID)
    	ON UPDATE CASCADE
        ON DELETE CASCADE
);

CREATE TABLE LikedBy (
	EMail VARCHAR(30) NOT NULL, 
    PostID INTEGER NOT NULL, 
    Date_ DATETIME, 
    CONSTRAINT LikedBy_PK PRIMARY KEY (EMail, PostID), 
    CONSTRAINT LikedBy_FK1 FOREIGN KEY (Email) REFERENCES User_(EMail)
    	ON UPDATE CASCADE
        ON DELETE NO ACTION,
    CONSTRAINT LikedBy_FK2 FOREIGN KEY (PostID) REFERENCES Post(PostID)
    	ON UPDATE CASCADE
        ON DELETE CASCADE
);

CREATE TABLE ReadBy (
	EMail VARCHAR(30) NOT NULL, 
    PostID INTEGER NOT NULL, 
    Date_ DATETIME, 
    CONSTRAINT ReadBy_PK PRIMARY KEY (EMail, PostID), 
    CONSTRAINT ReadBy_FK1 FOREIGN KEY (Email) REFERENCES User_(EMail)
    	ON UPDATE CASCADE
        ON DELETE CASCADE,
    CONSTRAINT ReadBy_FK2 FOREIGN KEY (PostID) REFERENCES Post(PostID)
    	ON UPDATE CASCADE
        ON DELETE CASCADE
);