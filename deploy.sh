echo "准备工作"
sed -i '.backup' 's/active: dev/active: prod/g' ./src/main/resources/application.yml
echo "清除上次的打包文件"
rm -r target/server-*
ssh aliyun "rm -r server-*.jar; echo '远程服务器文件目录:'; ls"
mvn clean
echo "生成API文档"
mvn -Dfile.encoding=UTF-8 smart-doc:html
echo "打包"
mvn package
echo "上传文件"
file=$(ls target | grep "server.*jar$")
echo target/$file
scp target/$file aliyun:/root
echo "开始部署"
PID=$(ssh aliyun "ps aux | grep 'java -jar server-.*jar$' | tr -s ' ' | cut -d ' ' -f 2")
echo $PID
ssh aliyun "kill -9 $PID"
ssh aliyun "nohup java -jar $file > medical.txt 2>&1 &"
echo "检查"
ssh aliyun "ps aux | grep java"
echo "清理工作"
cat ./src/main/resources/application.yml.backup > ./src/main/resources/application.yml
rm ./src/main/resources/application.yml.backup
