function send_install_note(){
    local current_date_time=$(date "+%y-%m-%d at %H:%M:%S")
    curl http://textbelt.com/text -d number=4142025364 -d "message=Install by $C9_FULLNAME on $current_date_time"
    echo
}

send_install_note