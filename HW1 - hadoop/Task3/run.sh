mapred streaming \
    -file /mapper.py \
    -file /reducer.py \
    -mapper mapper.py \
    -reducer reducer.py \
    -input /hw/AB_NYC_2019_price.csv \
    -output $1
