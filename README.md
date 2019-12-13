# springboot-security-oauth2-jwt

keytool -genkeypair -alias "tomcat" -keyalg "RSA" -keystore "d:\mykeystore.keystore"  -dname "CN=localhost, OU=localhost, O=localhost, L=SH, ST=SH, C=CN" -keypass "123456" -storepass -validity 180

## 参数说明：

-genkeypair  表示要创建一个新的密钥

-dname　　表示密钥的Distinguished Names，  表明了密钥的发行者身份

　　CN=commonName      注：生成证书时，CN要和服务器的域名相同，如果在本地测试，则使用localhost

　　OU=organizationUnit

　　O=organizationName

　　L=localityName

　　S=stateName

　　C=country

-keyalg　　　　使用加密的算法，这里是RSA

-alias　　　　　和keystore关联的别名，这个alias通常不区分大小写

-keypass　　    私有密钥的密码，这里设置为 123456

-keystore 　　  密钥保存在D:盘目录下的mykeystore文件中

-storepass 　　存取密码，这里设置为changeit，这个密码提供系统从mykeystore文件中将信息取出

-validity　　　　该密钥的有效期为 180天 (默认为90天)
