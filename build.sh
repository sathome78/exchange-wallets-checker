#!/usr/bin/env bash


docker build -t wallet-checker-app .

mv /Users/Nikita/Documents/exrates-wallet-checker/target/wallet-checker-app.jar /Users/Nikita/Documents

ssh -i ndmitrenko.pem ndmitrenko@172.31.3.72

scp -i ndmitrenko.pem wallet-checker-app.jar ndmitrenko@172.31.3.72:~/