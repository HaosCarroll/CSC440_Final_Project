function init(){
    switch_repo_to_dev_branch
    run_cloud9_setup_scripts
}

function run_cloud9_setup_scripts(){
    local called_from_directory="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
    local setup_script_name_and_path=$(printf "%s/z_scripts/setup_c9_to_work_on_repo.bash" $called_from_directory)  
    bash $setup_script_name_and_path
}

function switch_repo_to_dev_branch(){
    local called_from_directory="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
    cd $called_from_directory
    git checkout develop
    cd $GOPATH    
}

init