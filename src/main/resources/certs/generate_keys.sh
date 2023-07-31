#!/bin/sh
# create key pair
openssl genrsa -out keypair.pem 2048

# extract public key
openssl rsa -in keypair.pem -pubout -out public.pem

# extract private key
openssl pkcs8 -in keypair.pem -topk8 -nocrypt -inform PEM -outform PEM -out private.pem
