#!/bin/sh

# Source:
# https://help.github.com/articles/syncing-a-fork

GITHUB_ORIGINAL=$1

git remote -v
git remote add upstream $GITHUB_ORIGINAL
git fetch upstream
git checkout master
git merge upstream/master
git push