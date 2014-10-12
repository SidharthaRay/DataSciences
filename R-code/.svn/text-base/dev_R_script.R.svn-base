library(reshape2)
library(recommenderlab)
library(yaml)
# Load RJDBC library
library(rJava)
library(RJDBC)


config = yaml.load_file("config.yml")

#intRating = read.csv("C:/Projects/Customer_Next_Offer/NPTB_NBP_NBO_NBA/R/Soumya/interest_rating.csv")
intRating = read.csv(config$inputPath)

aggr.intRating <- aggregate(intRating,by=list(intRating$Userid,intRating$ProductId),FUN=mean, simplify=TRUE)
frame.intRating <- data.frame(cbind(aggr.intRating$Userid,aggr.intRating$ProductId,aggr.intRating$Interestrating))

realRM <- as(frame.intRating, "realRatingMatrix")

realDimNames <- dimnames(realRM)

switch(config$method,
       POPULAR={
         recommendationModel1 <- Recommender(realRM, method = "POPULAR")  
         # testRating - Different from Training Set
         testRating = read.csv(config$testPath)
         
         
         realTestRM <- as(testRating, "realRatingMatrix")
         
         predictions <- predict(recommendationModel1, realTestRM, type="topNList")
       },
       IBCF={
         recommendationModel2 <- Recommender(realRM, method = "IBCF", param=list(normalize = "Z-score",method="Jaccard",minRating=0))
         predictions <- predict(recommendationModel2, realRM, type="topNList")
       },
       UBCF={
         recommendationModel3 <- Recommender(realRM, method = "UBCF", param=list(normalize = "Z-score",method="Cosine",nn=5, minRating=0))
         predictions <- predict(recommendationModel3, realRM, type="topNList")
         predictions2 <- predict(recommendationModel3, realRM, type="ratings")
         dimnames(predictions2) <- realDimNames
       },
       SVD={
         recommendationModel4 <- Recommender(realRM, method = "SVD")
         predictions <- predict(recommendationModel4, realRM, type="topNList")
       }
)

top3Predictions <- bestN(predictions, n = 3)

frame.top3Predictions <- data.frame(Reduce(rbind, as(top3Predictions,"list")))

ratingPrediction <- as(predictions2,"data.frame")

colnames(ratingPrediction) <- c("UserId","ProductId","Rating")

# Create connection driver and open connection
jdbcDriver <- JDBC(driverClass=config$driverClass, classPath=config$classPath)
jdbcConnection <- dbConnect(jdbcDriver, "jdbc:vertica://10.25.36.162:5433/EDMP", "dbadmin", "Password1")

# Query on the Vertica instance name.
# instanceName <- dbGetQuery(jdbcConnection, "SELECT IPADDRESS FROM INTERPRETER_15MIN")
# dbSendUpdate(jdbcConnection,"INSERT INTO RECOMMENDATION VALUES(?,?,?)", i$user, i$item, i$rating)
dbWriteTable(jdbcConnection,'RECOMMENDATION',ratingPrediction,row.names=FALSE)
# print(instanceName)

# Close connection
dbDisconnect(jdbcConnection)


