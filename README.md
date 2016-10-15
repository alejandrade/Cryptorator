# Cryptorator
A quick Java Jersey API that encrypts string

## Instructions
This will run on 8080
Currently it is designed to work on any computer with minimal librarys and no JCE set to encrypt at AES CBC 128 with PLCS5Padding however you should be able to change it to what you need in the Crypto.properties file. I have extensiely tested this application.

###Endpoints
| Method | Path | Request  | Response |
| ------------- |:-------------:| -----:| --- |
| GET | /cr/{content} | path-param | {"content":"{encrypted text}","id":"{salt}"}  |
| GET | /ecr/{content}/{salt} | {"content":"{encrypted text}","id":"{salt}"} | {"content":"{unencrypted text}","id":"{salt}"} | 
| POST | /cr | {"content":"{text}"} | {"content":"{encrypted text}","id":"{salt}"} |
| POST | /ec2 | {"content":"{encrypted text}","id":"{salt}"} | {"content":"{unencrypted text}","id":"{salt}"} |
