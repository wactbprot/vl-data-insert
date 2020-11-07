#!/bin/sh

echo "========= clean up =========\n\n"
rm -r target/doc/*
rm -r target/coverage/*
rm -r docs/api/*
rm -r docs/coverage/*

echo "========= coverage =========\n\n"
lein cloverage
cp -r target/coverage/* docs/coverage

echo "=========  codox   =========\n\n"
lein codox
cp -r target/doc/* docs/api

