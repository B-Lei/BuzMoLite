#File used to run BuzMo
ant clean
ant all
cd out/production/BuzMo
mkdir Resources
#touch 2016.11.30.log
#touch 2016.11.30.sql
java -cp .:../../../Dependencies/ql-connector-java-5.1.40-bin.jar:../../../Dependencies/ojdbc6.jar BuzMo.BuzMo
