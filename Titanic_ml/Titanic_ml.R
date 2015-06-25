# Set working directory and import train datafiles to the top of your file
setwd("C:/Sidharth/DataSciences/Titanic_ml")
train <- read.csv("C:/Sidharth/DataSciences/Titanic_ml/train.csv")
View(train)

# A quick look at the structure of the dataframe, ie the types of variables that were loaded.
str(train)
table(train$Survived)
prop.table(table(train$Survived))

test <- read.csv("C:/Sidharth/DataSciences/Titanic_ml/test.csv")
View(test)
test$Survived <- rep(0, 418)

# 1st prediction - CSV file to be submitted
submit <- data.frame(PassengerId = test$PassengerId, Survived = test$Survived)
write.csv(submit, file = "theyallperish.csv", row.names = FALSE)

table(train$Sex)
prop.table(table(train$Sex, train$Survived), 1)

# 2nd prediction - CSV file to be submitted
test$Survived <- 0
test$Survived[test$Sex == 'female'] <- 1
submit <- data.frame(PassengerId = test$PassengerId, Survived = test$Survived)
write.csv(submit, file = "maleallperish.csv", row.names = FALSE)
table(submit$Survived)

summary(train$Age)

train$Child <- 0
train$Child[train$Age < 18] <- 1
aggregate(Survived ~ Child + Sex, data=train, FUN=length)
aggregate(Survived ~ Child + Sex, data=train, FUN=function(x) {sum(x)/length(x)})

train$Fare2 <- '30+'
train$Fare2[train$Fare < 30 & train$Fare >= 20] <- '20-30'
train$Fare2[train$Fare < 20 & train$Fare >= 10] <- '10-20'
train$Fare2[train$Fare < 10] <- '<10'
aggregate(Survived ~ Fare2 + Pclass + Sex, data=train, FUN=function(x) {sum(x)/length(x)})

test$Survived <- 0
test$Survived[test$Sex == 'female'] <- 1
test$Survived[test$Sex == 'female' & test$Pclass == 3 & test$Fare >= 20] <- 0
submit <- data.frame(PassengerId = test$PassengerId, Survived = test$Survived)
write.csv(submit, file = "result.csv", row.names = FALSE)
table(submit$Survived)


library(rpart)
library(rattle)
library(rpart.plot)
library(RColorBrewer)
#fit <- rpart(Survived ~ Pclass + Sex + Age + SibSp + Parch + Fare + Embarked, data=train, method="class")
fit <- rpart(Survived ~ Pclass + Sex + Age + SibSp + Parch + Fare + Embarked, data=train, method="class", control=rpart.control(minsplit=2, cp=0))
plot(fit)
text(fit)
fancyRpartPlot(fit)

Prediction <- predict(fit, test, type = "class")
submit <- data.frame(PassengerId = test$PassengerId, Survived = Prediction)
write.csv(submit, file = "result.csv", row.names = FALSE)

