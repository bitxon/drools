# Drools usage examples

## Case 1
#### Simple DRL file with all rules in it
- [Test/Executor](src/test/java/bitxon/drools/case1/Case1Test.java)
- [DRL file](src/main/resources/rules/case1/rank-calculation.drl)

## Case 2
#### Simple Microsoft Excel file with all rules in it
- [Test/Executor](src/test/java/bitxon/drools/case2/Case2Test.java)
- [Excel file](src/main/resources/rules/case2/rank-calculation.xlsx)

## Case 3
#### Multiple DRL files where one rule depends on another
- [Test/Executor](src/test/java/bitxon/drools/case3/Case3Test.java)
- [DRL file #1](src/main/resources/rules/case3/1-rank-calculation.drl)
- [DRL file #2](src/main/resources/rules/case3/2-bonus-calculation.drl)
- [DRL file #3](src/main/resources/rules/case3/3-damage-calculation.drl)

## Case 4
#### DRL template with Array Data Provider
- [Test/Executor](src/test/java/bitxon/drools/case4/Case4aTest.java)
- [DRL template](src/main/resources/rules/case4/rank-calculation-template.drl)
#### DRL template with Object Data Provider
- [Test/Executor](src/test/java/bitxon/drools/case4/Case4bTest.java)
- [DRL template](src/main/resources/rules/case4/rank-calculation-template.drl)
#### DRL template with Microsoft Excel file as data source
- [Test/Executor](src/test/java/bitxon/drools/case4/Case4cTest.java)
- [DRL template](src/main/resources/rules/case4/rank-calculation-template.drl)
- [Excel data](src/main/resources/rules/case4/rank-calculation-attributes.xlsx)
  