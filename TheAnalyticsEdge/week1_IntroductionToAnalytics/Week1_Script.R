setwd("C:/Sidharth/DataSciences/TheAnalyticsEdge/workspace")

#=========================
#Week-1 Lecture exercises
#=========================
WHO <- read.csv("C:/Sidharth/DataSciences/TheAnalyticsEdge/workspace/WHO.csv")
View(WHO)
str(WHO)
mean(WHO$Over60)
WHO$Country[which.min(WHO$Over60)]
WHO$Country[which.max(WHO$LiteracyRate)]
tapply(WHO$ChildMortality, WHO$Region, mean)

#===================================================
#Week-1 Problems(An analytical detective)
#===================================================
mvt <- read.csv("mvtWeek1.csv")
str(mvt)
max(mvt$ID)
min(mvt$Beat)
table(mvt$Arrest)
length(mvt$LocationDescription[mvt$LocationDescription=="ALLEY"])
mvt[1,]
DateConvert <- as.Date(strptime(mvt$Date, "%m/%d/%y %H:%M"))
summary(DateConvert)
mvt$Month <- months(DateConvert)
mvt$Weekday <- weekdays(DateConvert)
mvt$Date <- DateConvert
table(mvt$Month)
min(table(mvt$Month))
table(mvt$Weekday)
max(table(mvt$Weekday))
table(mvt$Arrest, mvt$Month)
hist(mvt$Date, breaks=100)
boxplot(mvt$Date ~ mvt$Arrest)
mvt$Year <- as.numeric(format(mvt$Date, "%Y"))
gen.arrest.table <- with(mvt, table(Year, Arrest))
prop.table(gen.arrest.table, 1)
sort(table(mvt$LocationDescription))

Top5 <- subset(mvt, 
               mvt$LocationDescription == "STREET" 
               | mvt$LocationDescription == "PARKING LOT/GARAGE(NON.RESID.)"
               | mvt$LocationDescription == "ALLEY"
               | mvt$LocationDescription == "GAS STATION"
               | mvt$LocationDescription == "DRIVEWAY - RESIDENTIAL")
nrow(Top5)
Top5$LocationDescription = factor(Top5$LocationDescription)
table(Top5$LocationDescription)
str(Top5$LocationDescription)

gen.arrest.table.top5 <- with(Top5, table(LocationDescription, Arrest))
prop.table(gen.arrest.table.top5, 1)

theftAtGasStation <- subset(mvt, mvt$LocationDescription == "GAS STATION")
sort(table(theftAtGasStation$Weekday))

theftAtResdDriveways <- subset(mvt, mvt$LocationDescription == "DRIVEWAY - RESIDENTIAL")
sort(table(theftAtResdDriveways$Weekday))

#===================================================
#Week-1 Problems(Stock dynamics)
#===================================================
IBM <- read.csv("IBMStock.csv")
GE <- read.csv("GEStock.csv")
ProcterGamble <- read.csv("ProcterGambleStock.csv")
CocaCola <- read.csv("CocaColaStock.csv")
Boeing <- read.csv("BoeingStock.csv")

IBM$Date <- as.Date(IBM$Date, "%m/%d/%y")
GE$Date = as.Date(GE$Date, "%m/%d/%y")
CocaCola$Date = as.Date(CocaCola$Date, "%m/%d/%y")
ProcterGamble$Date = as.Date(ProcterGamble$Date, "%m/%d/%y")
Boeing$Date = as.Date(Boeing$Date, "%m/%d/%y")

sort(table(as.numeric(format(IBM$Date, "%Y"))))
mean(IBM$StockPrice)
min(GE$StockPrice)
max(CocaCola$StockPrice)
median(Boeing$StockPrice)
sd(ProcterGamble$StockPrice)

plot(x=CocaCola$Date, y=CocaCola$StockPrice, type="l", col="red")
lines(ProcterGamble$Date, ProcterGamble$StockPrice, col="blue")
abline(v=as.Date(c("2000-03-01")), lwd=2)
abline(v=as.Date(c("1983-01-01")), lwd=2)

plot(CocaCola$Date[301:432], CocaCola$StockPrice[301:432], type="l", col="red", ylim=c(0,210))
lines(IBM$Date, IBM$StockPrice, col="blue")
lines(GE$Date, GE$StockPrice, col="green")
lines(ProcterGamble$Date, ProcterGamble$StockPrice, col="purple")
lines(Boeing$Date, Boeing$StockPrice, col="orange")
abline(v=as.Date(c("2000-03-01")), lwd=1)
abline(v=as.Date(c("2005-01-01")), lwd=1)
abline(v=as.Date(c("1997-09-01")), lwd=1)
abline(v=as.Date(c("1997-11-01")), lwd=1)
abline(v=as.Date(c("2004-01-01")), lwd=1)
abline(v=as.Date(c("2005-12-31")), lwd=1)

tapply(IBM$StockPrice, months(IBM$Date), "mean")
tapply(GE$StockPrice, months(IBM$Date), "mean")
tapply(CocaCola$StockPrice, months(IBM$Date), "mean")
tapply(ProcterGamble$StockPrice, months(IBM$Date), "mean")
tapply(Boeing$StockPrice, months(IBM$Date), "mean")
mean(IBM$StockPrice)
mean(GE$StockPrice)
mean(CocaCola$StockPrice)
mean(ProcterGamble$StockPrice)
mean(Boeing$StockPrice)

#========================================================================
#Week-1 Problems(Demographics and employment in the united states)
#========================================================================
CPS <- read.csv("CPSData.csv")
nrow(CPS)
str(CPS)
sort(table(CPS$Industry))
sort(table(CPS$State))
table(CPS$Citizenship)
table(CPS$Hispanic, CPS$Race)
length(CPS$PeopleInHousehold[is.na(CPS$PeopleInHousehold)])
length(CPS$Region[is.na(CPS$Region)])
length(CPS$State[is.na(CPS$State)])
length(CPS$MetroAreaCode[is.na(CPS$MetroAreaCode)])
length(CPS$Age[is.na(CPS$Age)])
length(CPS$Married[is.na(CPS$Married)])
length(CPS$Sex[is.na(CPS$Sex)])
length(CPS$Education[is.na(CPS$Education)])
length(CPS$Race[is.na(CPS$Race)])
length(CPS$Hispanic[is.na(CPS$Hispanic)])
length(CPS$CountryOfBirthCode[is.na(CPS$CountryOfBirthCode)])
length(CPS$Citizenship[is.na(CPS$Citizenship)])
length(CPS$EmploymentStatus[is.na(CPS$EmploymentStatus)])
length(CPS$Industry[is.na(CPS$Industry)])

gen.married.region <- with(CPS, table(CPS$Region, is.na(CPS$Married)))
prop.table(gen.married.region)
gen.married.sex <- with(CPS, table(Sex, is.na(Married)))
prop.table(gen.married.sex)
gen.married.age <- with(CPS, table(Age, is.na(Married)))
prop.table(gen.married.age)
gen.married.citizenship <- with(CPS, table(Citizenship, is.na(Married)))
prop.table(gen.married.citizenship)


gen.metropolitan.region <- table(CPS$Region, is.na(CPS$MetroAreaCode))
prop.table(gen.metropolitan.region)

tapply(is.na(CPS$MetroAreaCode), CPS$State, "mean")

MetroAreaMap <- read.csv("MetroAreaCodes.csv")
CountryMap <- read.csv("CountryCodes.csv")
CPS = merge(CPS, MetroAreaMap, by.x="MetroAreaCode", by.y="Code", all.x=TRUE)
str(CPS)
is.na(CPS$MetroArea)
sort(table(CPS$MetroArea))
sort(tapply(CPS$Hispanic, CPS$MetroArea, "mean"))
sort(tapply(CPS$Race == "Asian", CPS$MetroArea, "mean"))

sort(tapply(CPS$Education == "No high school diploma", CPS$MetroArea, mean, na.rm=TRUE))

CPS = merge(CPS, CountryMap, by.x="CountryOfBirthCode", by.y="Code", all.x=TRUE)
sort(table(CPS$Country))
table(is.na(CPS$Country))
sort(table(CPS$Country))

sort(table(CPS$Country))  #Philippines

notNY <- subset(CPS, CPS$MetroArea=="New York-Northern New Jersey-Long Island, NY-NJ-PA")
nrow(notNY)
notNYnotUS <- subset(notNY, notNY$Country!="United States")
nrow(notNYnotUS)
nrow(notNYnotUS)/nrow(notNY)


India <- subset(CPS, CPS$Country=="India")
sort(table(India$MetroArea))
Brazil <- subset(CPS, CPS$Country=="Brazil")
sort(table(Brazil$MetroArea))
Somalia <- subset(CPS, CPS$Country=="Somalia")
sort(table(Somalia$MetroArea))

#========================================================================
#Week-1 Problems(Internet privacy poll (OPTIONAL))
#========================================================================
poll <- read.csv("AnonymityPoll.csv")
summary(poll)
str(poll)
summary(poll$Smartphone)
table(poll$Smartphone)
table(is.na(poll$Smartphone))
length(poll$Smartphone)
names(poll)
polltable(poll$Region, poll$State)

nrow(subset(poll, poll$Internet.Use == 0 & poll$Smartphone == 0))
str(poll$Internet.Use)
nrow(subset(poll, poll$Internet.Use == 1 & poll$Smartphone == 1))
nrow(subset(poll, poll$Internet.Use == 1 & poll$Smartphone == 0))
nrow(subset(poll, poll$Internet.Use == 0 & poll$Smartphone == 1))
table(is.na(poll$Internet.Use))
table(is.na(poll$Smartphone))
limited <- subset(poll, poll$Internet.Use == 1 | poll$Smartphone == 1)
sort(sapply(limited, function(x) length(which(is.na(x)))))
mean(limited$Info.On.Internet)
table(limited$Info.On.Internet)
with(limited, table(limited$Worry.About.Info)) -> gen.WorryAbtInternet
prop.table(gen.WorryAbtInternet, 1)
