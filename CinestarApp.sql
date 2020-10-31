CREATE DATABASE CinestarApp
GO
USE CinestarApp
GO

CREATE TABLE Actor
(
	IDActor INT PRIMARY KEY IDENTITY,
	FirstName NVARCHAR(50) NOT NULL,
	LastName NVARCHAR(50)
)
GO

CREATE TABLE Director
(
	IDDirector INT PRIMARY KEY IDENTITY,
	FirstName NVARCHAR(50) NOT NULL,
	LastName NVARCHAR(50)
)
GO

CREATE TABLE Movie
(
	IDMovie INT PRIMARY KEY IDENTITY,
	Title NVARCHAR(50) NOT NULL,
	PublishDate NVARCHAR(50) NOT NULL,
	MovieDescription NVARCHAR(MAX) NOT NULL,
	OriginalTitle NVARCHAR(50) NOT NULL,
	DirectorID INT NOT NULL,
	Duration INT NOT NULL,
	Genre NVARCHAR(100) NOT NULL,
	PicturePath NVARCHAR(100),
	Link NVARCHAR(100) NOT NULL,
	StartDate NVARCHAR(50) NOT NULL
	FOREIGN KEY (DirectorID) REFERENCES Director(IDDirector)
)
GO

CREATE TABLE Movies_Actors
(
	MovieID INT NOT NULL,
	ActorID INT NOT NULL,
	FOREIGN KEY (MovieID) REFERENCES Movie(IDMovie),
	FOREIGN KEY (ActorID) REFERENCES Actor(IDActor)
)
GO

CREATE TABLE AppUser
(
	IDAppUser INT PRIMARY KEY IDENTITY,
	Username NVARCHAR(25) NOT NULL,
	Pswrd NVARCHAR(50) NOT NULL,
	IsAdmin BIT NOT NULL
)
GO

CREATE PROC DeleteAll
AS
BEGIN
	DELETE FROM Movie
	DELETE FROM Director
	DELETE FROM Actor
END
GO

/*Movie CRUD procedures*/
CREATE PROC spCreateMovie
	@IDMovie INT OUTPUT,
	@Title NVARCHAR(50),
	@PublishDate NVARCHAR(50),
	@Description NVARCHAR(MAX),
	@OriginalTitle NVARCHAR(50),
	@DirectorID INT,
	@Duration INT,
	@Genre NVARCHAR(100),
	@PicturePath NVARCHAR(100),
	@Link NVARCHAR(100),
	@StartDate NVARCHAR(50)
AS
BEGIN
	INSERT INTO Movie VALUES (@Title, @PublishDate, @Description, @OriginalTitle, @DirectorID, @Duration, @Genre, @PicturePath, @Link, @StartDate)
	SET @IDMovie = SCOPE_IDENTITY()
END
GO

CREATE PROC spSelectMovie
	@IDMovie INT
AS
BEGIN
	SELECT * FROM Movie WHERE IDMovie = @IDMovie	
END
GO

CREATE PROC spSelectMovies
AS
BEGIN
	SELECT * FROM Movie
END
GO

CREATE PROC spUpdateMovie
	@IDMovie INT,
	@Title NVARCHAR(50),
	@PublishDate NVARCHAR(50),
	@Description NVARCHAR(MAX),
	@OriginalTitle NVARCHAR(50),
	@DirectorID INT,
	@Duration INT,
	@Genre NVARCHAR(100),
	@PicturePath NVARCHAR(100),
	@Link NVARCHAR(100),
	@StartDate NVARCHAR(50)
AS
BEGIN
	UPDATE Movie SET 
	Title = @Title,
	PublishDate = @PublishDate,
	MovieDescription = @Description,
	OriginalTitle = @OriginalTitle,
	DirectorID = @DirectorID,
	Duration = @Duration,
	Genre = @Genre,
	PicturePath = @PicturePath,
	Link = @Link,
	StartDate = @StartDate
	WHERE IDMovie = @IDMovie
END
GO

CREATE PROC spDeleteMovie
	@IDMovie INT
AS
BEGIN
	DELETE FROM Movie WHERE IDMovie = @IDMovie
END
GO

/*Actor CRUD procedures*/
CREATE PROC spCreateActor
	@IDActor INT OUTPUT,
	@FirstName NVARCHAR(50),
	@LastName NVARCHAR(50)
AS
BEGIN
	IF NOT EXISTS (SELECT * FROM Actor WHERE FirstName = @FirstName AND LastName = @LastName GROUP BY FirstName,LastName)
	BEGIN
		INSERT INTO Actor VALUES (@FirstName, @LastName)
		SET @IDActor = SCOPE_IDENTITY()
	END
	ELSE
	BEGIN
		SELECT @IDActor = IDActor FROM Actor WHERE FirstName = @FirstName AND LastName = @LastName
	END
END
GO

CREATE PROC spSelectActor
	@IDActor INT
AS
BEGIN
	SELECT * FROM Actor WHERE IDActor = @IDActor
END	
GO

CREATE PROC spSelectActors
AS
BEGIN
	SELECT * FROM Actor
END
GO

CREATE PROC spUpdateActor
	@IDActor INT,
	@FirstName NVARCHAR(50),
	@LastName NVARCHAR(50)
AS
BEGIN
	UPDATE Actor SET
	FirstName = @FirstName,
	LastName = @LastName
	WHERE IDActor = @IDActor
END
GO

CREATE PROC spDeleteActor
	@IDActor INT
AS
BEGIN
	DELETE FROM Actor WHERE IDActor = @IDActor
END
GO

/*Director CRUD procedures*/
CREATE PROC spCreateDirector
	@IDDirector INT OUTPUT,
	@FirstName NVARCHAR(50),
	@LastName NVARCHAR(50)
AS
BEGIN
	IF NOT EXISTS (SELECT * FROM Director WHERE FirstName = @FirstName AND LastName = @LastName)
	BEGIN
		INSERT INTO Director VALUES (@FirstName, @LastName)
		SET @IDDirector = SCOPE_IDENTITY()
	END
	ELSE
	BEGIN
		SELECT @IDDirector = IDDirector FROM Director WHERE FirstName = @FirstName AND LastName = @LastName
	END
END
GO

CREATE PROC spSelectDirector
	@IDDirector INT
AS
BEGIN
	SELECT * FROM Director WHERE IDDirector = @IDDirector
END	
GO

CREATE PROC spSelectDirectors
AS
BEGIN
	SELECT * FROM Director
END
GO

CREATE PROC spUpdateDirector
	@IDDirector INT,
	@FirstName NVARCHAR(50),
	@LastName NVARCHAR(50)
AS
BEGIN
	UPDATE Director SET
	FirstName = @FirstName,
	LastName = @LastName
	WHERE IDDirector = @IDDirector
END
GO

CREATE PROC spDeleteDirector
	@IDDirector INT
AS
BEGIN
	DELETE FROM Director WHERE IDDirector = @IDDirector
END
GO

/*USER procedures*/
CREATE PROC spCreateUser
	@IDUser INT OUTPUT,
	@username NVARCHAR(25),
	@password NVARCHAR(50),
	@isAdmin BIT
AS
BEGIN
	INSERT INTO AppUser VALUES (@username, @password, @isAdmin)
	SET @IDUser = SCOPE_IDENTITY()
END
GO

CREATE PROC spSelectUsers
AS 
BEGIN 
	SELECT * FROM AppUser
END
GO

CREATE PROC spSelectUser
	@IDUser INT OUTPUT
AS
BEGIN
	SELECT * FROM AppUser WHERE IDAppUser = @IDUser
END
GO

CREATE PROC spFindUser
	@Username NVARCHAR(25),
	@Password NVARCHAR(50)
AS
BEGIN
	SELECT * FROM AppUser WHERE Username = @Username AND Pswrd = @Password
END
GO

CREATE PROC spGetUser
	@Username NVARCHAR(25),
	@Password NVARCHAR(50),
	@Exists BIT OUTPUT
AS
BEGIN
	IF EXISTS (SELECT * FROM AppUser WHERE Username = @Username AND Pswrd = @Password)
	BEGIN
		SET @Exists = 1
	END
	ELSE
	BEGIN
		SET @Exists = 0
	END
END
GO

CREATE PROC spCheckUsername
	@Username NVARCHAR(25),
	@Exists INT OUTPUT
AS
BEGIN
	IF EXISTS (SELECT * FROM AppUser WHERE Username = @Username)
	BEGIN
		SET @Exists = 1
	END
	ELSE
	BEGIN
		SET @Exists = 0
	END
END
GO

CREATE PROC spUpdateUser
	@IDUser INT,
	@Username NVARCHAR(25),
	@Password NVARCHAR(50),
	@IsAdmin BIT
AS
BEGIN
	UPDATE AppUser SET Username = @Username, Pswrd = @Password, IsAdmin = @IsAdmin WHERE IDAppUser = @IDUser
END
GO

CREATE PROC spDeleteUser
	@id INT
AS
BEGIN
	DELETE FROM AppUser WHERE IDAppUser = @id
END
GO

/*Movies_Actors procedures*/
CREATE PROC spSelectMoviesWithActor
	@ActorID INT
AS
BEGIN
	SELECT m.* FROM Movie AS m
	INNER JOIN Movies_Actors AS ma ON ma.MovieID = m.IDMovie
	WHERE ma.ActorID = @ActorID
END
GO

CREATE PROC spSelectActorsInMovie
	@MovieID INT
AS
BEGIN
	SELECT a.* FROM Actor AS a
	INNER JOIN Movies_Actors AS ma ON ma.ActorID = a.IDActor
	WHERE ma.MovieID = @MovieID
END
GO

CREATE PROC spLinkActorAndMovie
	@MovieID INT,
	@ActorID INT
AS
BEGIN
	INSERT INTO Movies_Actors VALUES (@MovieID, @ActorID)
END
GO

CREATE PROC spUnlinkMovieWithActors
	@IDMovie INT
AS
BEGIN 
	DELETE FROM Movies_Actors WHERE MovieID = @IDMovie
END
GO

CREATE PROC spUnlinkActorWithMovies
	@IDActor INT
AS
BEGIN
	DELETE FROM Movies_Actors WHERE ActorID = @IDActor
END
GO

/* Delete All data procedure*/
CREATE PROC spDeleteAll
AS
BEGIN
	DELETE FROM Movies_Actors
	DELETE FROM Actor
	DELETE FROM Movie
	DELETE FROM Director
END
GO