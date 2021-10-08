import pandas as pd
import numpy as np

data = pd.read_csv('AB_NYC_2019.csv')
print('no NANS', np.mean(data[data['price'].notna()]['price']))
data = data['price']
print('AVG value:', np.mean(data))
print('VAR value:', np.var(data))
data.to_csv('AB_NYC_2019_price.csv', index=False, header=False)
