mkdir -p $GOPATH/data/log/
clear
printf "\nStarting MongoDB!!\n\n"
mongod --bind_ip=$IP --dbpath=data --nojournal --rest "$@" --fork --logpath=$GOPATH/data/log/mongodb.log
printf "\n\nUse VM kill process to stop until something better gets figured out...\n"
