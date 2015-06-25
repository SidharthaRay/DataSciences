setwd("C:/Sidharth/DataSciences/TheAnalyticsEdge/workspace")

#Avoid scientific representation of floating point numbers
options(scipen=999)

#=========================
#Week-2 Lecture exercises
#=========================
#Wine example
wine <- read.csv("wine.csv")
summary(wine)
names(wine)
RainModel <- lm(Price ~ WinterRain + HarvestRain, data=wine)
summary(RainModel)
model1 <- lm(Price ~ AGST + HarvestRain + WinterRain + Age + FrancePop, data=wine)
summary(model1)
model2 <- lm(Price ~ AGST + HarvestRain + WinterRain + Age, data=wine)
summary(model2)
model3 <- lm(Price ~ AGST + HarvestRain + WinterRain + FrancePop, data=wine)
summary(model3)
model4 <- lm(Price ~ AGST + HarvestRain + WinterRain, data=wine)
summary(model4)
plot(wine$WinterRain, log(wine$Price))
cor(wine)
cor(wine$WinterRain, wine$Price)
cor(wine$Age, wine$FrancePop)
cor(wine$WinterRain, wine$HarvestRain)
wineTest <- read.csv("wine_test.csv")
predict(model2, newdata=wineTest)

#Baseball example
baseball <- read.csv("baseball.csv")
str(baseball)
moneyball <- subset(baseball, baseball$Year < 2002)
str(moneyball)
moneyball$RD <- moneyball$RS - moneyball$RA
str(moneyball)
names(moneyball)
plot(moneyball$RD, moneyball$W)
WinsReg <- lm(W ~ RD, data=moneyball)
summary(WinsReg)
#80.8814 + 0.1058 * RD >= 95
(95 - 80.8814)/0.1058
80.8814 + (0.1058 * (713 - 614))
RunsReg <- lm(RS ~ OBP + SLG + BA, data=moneyball)    
summary(RunsReg)
RunsReg <- lm(RS ~ OBP + SLG, data=moneyball)    
summary(RunsReg)
-804.63 + (2737.77 * 0.311) + (1584.91 * 0.405)

Eric Chavez   0.338 	0.540 	                  $1,400,000      976.5877
-804.63 + (2737.77 * 0.338) + (1584.91 * 0.540)
Jeremy Giambi 	0.391 	0.450 	                $1,065,000      979.0476
-804.63 + (2737.77 * 0.391) + (1584.91 * 0.450)
Frank Menechino 	0.369 	0.374 	              $0,295,000      798.3635
-804.63 + (2737.77 * 0.369) + (1584.91 * 0.374)
Greg Myers 	0.313 	0.447 	                    $0,800,000      760.7468
-804.63 + (2737.77 * 0.313) + (1584.91 * 0.447)
Carlos Pena 	0.361 	0.500 	                  $0,300,000      976.16
-804.63 + (2737.77 * 0.361) + (1584.91 * 0.500)

ORunsReg <- lm(RA ~ OOBP + OSLG, data=moneyball)    
summary(ORunsReg)
-837.38 + (2913.60 * 0.297) + (1514.29 * 0.370)

#NBA example
NBA <- read.csv("NBA_train.csv")
str(NBA)
NBA$PTSdiff <- NBA$PTS - NBA$oppPTS
plot(NBA$PTSdiff, NBA$W)
WinsReg <- lm(W ~ PTSdiff, data=NBA)
summary(WinsReg)
names(NBA)
PointsReg <- lm(PTS ~ X2PA + X3PA + FTA + AST + ORB + DRB + TOV + STL + BLK, data=NBA)
summary(PointsReg)  
PointsReg2 <- lm(PTS ~ X2PA + X3PA + FTA + AST + ORB + DRB + STL + BLK, data=NBA)
summary(PointsReg2)  
PointsReg3 <- lm(PTS ~ X2PA + X3PA + FTA + AST + ORB + STL + BLK, data=NBA)
summary(PointsReg3)  
PointsReg4 <- lm(PTS ~ X2PA + X3PA + FTA + AST + ORB + STL, data=NBA)
summary(PointsReg4)  
SSE_4 <- sum(PointsReg4$residuals^2)
SSE_4
RMSE_4 <- sqrt(SSE/nrow(NBA))
RMSE_4
NBA_test <- read.csv("NBA_test.csv")
PointsPrediction <- predict(PointsReg4, newdata=NBA_test)
SSE <- sum((PointsPrediction - NBA_test$PTS)^2)
SST <- sum((mean(NBA$PTS) - NBA_test$PTS)^2)
R2 <- 1 - (SSE/SST)
R2
teamRank <- c(1, 2, 3, 3, 4, 4, 4, 4, 5, 5)
wins2012 <- c(94, 88, 95, 88, 93, 94, 98, 97, 93, 94) 
wins2013 <- c(97, 97, 92, 93, 92, 96, 94, 96, 92, 90)
length(wins2013)
cor(teamRank, wins2012)
cor(teamRank, wins2013)


#===================================================
#Week-2 Problems(Climate Change)
#===================================================
climate <- read.csv("climate_change.csv")
str(climateTraining)
climateTraining <- subset(climate, Year <= 2006)
climateTesting <- subset(climate, Year > 2006)
model1 <- lm(Temp ~ MEI + CO2 + CH4 + N2O + CFC.11 + CFC.12 + TSI + Aerosols, data=climateTraining)
summary(model1)
cor(climateTraining)
model2 <- lm(Temp ~ MEI + N2O + TSI + Aerosols, data=climateTraining)
summary(model2)
?step
model3 <- step(model1)
summary(model3)
model3Predict <- predict(model3, newdata=climateTesting)
SSE <- sum((model3Predict - climateTesting$Temp)^2)
SST <- sum((mean(climateTraining$Temp) - climateTesting$Temp)^2)
R2 <- 1 - SSE/SST
R2

#===================================================
#Week-2 Problems(Reading Test Scores)
#===================================================
pisaTrain <- read.csv("pisa2009train.csv")
pisaTest <- read.csv("pisa2009test.csv")
tapply(pisaTrain$readingScore, pisaTrain$male, mean)
sapply(pisaTrain, function(x) length(which(is.na(x))))
pisaTrain <- na.omit(pisaTrain)
pisaTest <- na.omit(pisaTest)
unique(pisaTrain$grade)
str(pisaTrain)
cols.to.factor <- sapply( pisaTrain, function(col) length(unique(col)) < log10(length(col)) )
pisaTrain[ , cols.to.factor] <- lapply(pisaTrain[ , cols.to.factor] , factor)
unique(pisaTrain$raceeth)
table(factor(pisaTrain$grade))
pisaTrain$raceeth <- relevel(pisaTrain$raceeth, "White")
pisaTest$raceeth <- relevel(pisaTest$raceeth, "White")
lmScore <- lm(readingScore ~ ., data=pisaTrain)
summary(lmScore)
RMSE <- sqrt(sum(lmScore$residuals^2)/nrow(pisaTrain))
RMSE
predTest <- predict(lmScore, newdata=pisaTest)
summary(predTest)
637.7 - 353.2
SSE = sum((predTest - pisaTest$readingScore)^2)
SSE
RMSE <- sqrt(sum((predTest - pisaTest$readingScore)^2)/nrow(pisaTest))
RMSE
mean(pisaTrain$readingScore)
SST <- sum((mean(pisaTrain$readingScore) - pisaTest$readingScore)^2)
SST
R2 <- 1 - SSE/SST
R2

#======================================================================
#Week-2 Problems(Detecting Flu Epidemics via Search Engine Query Data)
#======================================================================
fluTrain <- read.csv("FluTrain.csv")
str(fluTrain)
head(fluTrain)
sort(tapply(fluTrain$ILI, fluTrain$Week, max))
sort(tapply(fluTrain$Queries, fluTrain$Week, max))
hist(fluTrain$ILI)
summary(fluTrain$ILI)
plot(fluTrain$ILI, fluTrain$Queries)
plot(fluTrain$Queries, log(fluTrain$ILI))
FluTrend1 <- lm(log(ILI) ~ Queries, data=fluTrain)
summary(FluTrend1)
corIliQueries <- cor(log(fluTrain$ILI), fluTrain$Queries)
corIliQueries^2
log(1/corIliQueries)
exp(-0.5*corIliQueries)
fluTest <- read.csv("FluTest.csv")
PredTest1 <- exp(predict(FluTrend1, newdata=fluTest))
tgtIli <- PredTest1[11]
relError <- (fluTest$ILI[11] - tgtIli)/fluTest$ILI[11]
relError
RMSE <- sqrt(sum((PredTest1 - fluTest$ILI)^2)/nrow(fluTest))
RMSE
#Time series
install.packages("zoo")
library(zoo)
ILILag2 <- lag(zoo(fluTrain$ILI), -2, na.pad=TRUE)
fluTrain$ILILag2 <- coredata(ILILag2)
table(is.na(fluTrain$ILILag2))
plot(log(fluTrain$ILILag2), log(fluTrain$ILI))
FluTrend2 <- lm(log(ILI) ~ Queries + log(ILILag2), data=fluTrain)
summary(FluTrend2)
ILILag2 <- lag(zoo(fluTest$ILI), -2, na.pad=TRUE)
fluTest$ILILag2 <- coredata(ILILag2)
table(is.na(fluTest$ILILag2))
fluTest$ILILag2[1] <- fluTrain$ILI[(nrow(fluTrain)-1)]
fluTest$ILILag2[2] <- fluTrain$ILI[nrow(fluTrain)]
fluTest$ILILag2[1]
fluTest$ILILag2[2]
PredTest2 <- exp(predict(FluTrend2, newdata=fluTest))
RMSE <- sqrt(sum((PredTest2 - fluTest$ILI)^2)/nrow(fluTest))
RMSE

#=============================
#Week-2 Problems(State Data)
#=============================
data(state)
statedata = cbind(data.frame(state.x77), state.abb, state.area, state.center,  state.division, state.name, state.region)
str(statedata)
plot(statedata$x, statedata$y)
tapply(statedata$HS.Grad, statedata$state.region, mean)
boxplot(statedata$Murder ~ statedata$state.region)
northeastMurder <- subset(statedata, statedata$state.region == "Northeast")
which.max(northeastMurder$Murder)
northeastMurder[6,14]
names(statedata)
lifeExpModel <- lm(Life.Exp ~ Population + Income + Illiteracy + Murder + HS.Grad + Frost + state.area, data=statedata)
summary(lifeExpModel)
plot(statedata$Income, statedata$Life.Exp)
lifeExpModel2 <- lm(Life.Exp ~ Population + Income + Illiteracy + Murder + HS.Grad + Frost, data=statedata)
summary(lifeExpModel2)
lifeExpModel3 <- lm(Life.Exp ~ Population + Income + Murder + HS.Grad + Frost, data=statedata)
summary(lifeExpModel3)
lifeExpModel4 <- lm(Life.Exp ~ Population + Murder + HS.Grad + Frost, data=statedata)
summary(lifeExpModel4)



