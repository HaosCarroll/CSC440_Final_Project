function init(){
    setup_mongo_db_for_c9
}

function setup_mongo_db_for_c9(){
    cd $called_from_directory
    cd ..
    sudo apt-get update
    sudo apt-key adv --keyserver hkp://keyserver.ubuntu.com:80 --recv EA312927
    
    echo "deb http://repo.mongodb.org/apt/ubuntu trusty/mongodb-org/3.2 multiverse" | sudo tee /etc/apt/sources.list.d/mongodb-org-3.2.list
    sudo apt-get update
    sudo apt-get install -y mongodb-org
    local mongo_db_data_directory="data"
    local mongo_run_script_name="mongodb_run.bash"
    mkdir -p $mongo_db_data_directory
    
    echo 'mongod --bind_ip=$IP --dbpath=data --nojournal --rest "$@"' > $mongo_run_script_name
    chmod a+x $mongo_run_script_name
    cd $GOPATH
    #./$mongo_run_script_name
}

init
