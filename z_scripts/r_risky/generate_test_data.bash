#!/bin/bash


num_to_gen=9
    
    count=0;
    for k in `seq 1 $num_to_gen`;
    do
        for i in `seq 1 $num_to_gen`;
        do
            for j in `seq 1 $i`;
            do
            let "count=$count+1"
            done
        done
    done
    
    echo ""
    echo "      String[][] billableStringDataArray = new String[$count][];"
    echo ""
    
    count=0;

    for k in `seq 1 $num_to_gen`;
    do
        echo ""
        echo "// <--  Test Data Set # $k START!"
        echo ""
        for i in `seq 1 $num_to_gen`;
        do
            for j in `seq 1 $i`;
            do
                max=9
                let "randomNum = $RANDOM % $max +1"
                randomService=$(printf "%s%s%s%s%s%s" $randomNum $randomNum $randomNum $randomNum $randomNum $randomNum)
                let "randomNum = $RANDOM % $max +1"
                randomUser=$(printf "%s%s%s%s%s%s%s%s%s" $randomNum $randomNum $randomNum $randomNum $randomNum $randomNum $randomNum $randomNum $randomNum)
                let "randomNum = $RANDOM % $max +1"
                randomProvider=$(printf "%s%s%s%s%s%s%s%s%s" $randomNum $randomNum $randomNum $randomNum $randomNum $randomNum $randomNum $randomNum $randomNum)
    
                maxTwo=3
                maxTre=10
                let "randomNumOne = $RANDOM % $maxTwo"
                if [ "$randomNumOne" -gt "1" ]
                then
                    maxTre=5
                fi
                let "randomNumTwo = $RANDOM % $maxTre"
                randomHour=$(printf "%s%s" $randomNumOne $randomNumTwo)
                
                #echo "randomHour = $randomHour"
                
                if [ "$randomHour" -eq "24" ]
                then
                    randomHour="23"
                    randomMinute="59"
                    randomSecond="59"
                    #printf "* * * "
                else
                    maxMinit=60
                    let "randomMinute = $RANDOM % $maxMinit"
                    let "randomSecond = $RANDOM % $maxMinit"
                fi
                
                if [ "$j" -eq "1" ]
                then
                    randomTime="T21:00:01Z"
                elif [ "$j" -eq "$i" ]
                then
                    randomTime="T21:00:00Z"
                else
                    randomTime=$(printf "T%s:%s:%sZ" $randomHour $randomMinute $randomSecond)
                fi
                #echo "$randomTime"
                
                maxDollAmount=251
                maxCoinAMount=100
                let "randomCostDoll = $RANDOM % $maxDollAmount"
                let "randomCostDollars = $RANDOM % $maxCoinAMount"
                randomCost=$(printf "%s.%s" $randomCostDoll $randomCostDollars)
                #echo $randomCost
                let "countPlus=$count+1"
                
                maxMonth=11
                maxDate=28
                let "randomMonth = $RANDOM % $maxMonth +1"
                let "randomDay = $RANDOM % $maxDate +1"
                
                #echo "$randomMonth $randomDay"
                
                let "digit = $k%10"
                
                if [ "$digit" -eq "0" ]
                then
                    digit="1"
                fi
                providerNum=$(printf "%s%s%s%s%s%s%s%s%s" $digit $digit $digit $digit $digit $digit $digit $digit $digit)
                
                line_to_add="       billableStringDataArray[$count] = new String[]{\"$countPlus\", /*U*/\"$randomUser\", /*P*/\"$providerNum\", /*S*/\"$randomService\", \"$randomMonth/$randomDay/2016\", \"2016-$randomMonth-$randomDay$randomTime\", \"$randomCost\", \" Test Case : $k.$i.$j\"};"
                echo "$line_to_add"
                let "count=$count+1"
            done
            line_break_to_add=""
            echo $line_break_to_add
        done 
        echo ""
        echo "// <--  END Test Data Set # $k"
        echo ""
    done
exit 0