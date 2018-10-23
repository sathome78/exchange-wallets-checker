#!/usr/bin/env bash


docker build -t wallet-checker-app .

ssh -i ndmitrenko.pem ndmitrenko@172.31.3.72

scp -i ndmitrenko.pem wallet-checker-app.jar ndmitrenko@172.31.3.72:~/