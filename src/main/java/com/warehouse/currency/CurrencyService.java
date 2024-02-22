package com.warehouse.currency;

import java.util.List;

public interface CurrencyService {
    
    public List<Currency> findAllCurrency();
	
	public Currency findById(Long theId);
	
	public Currency save(Currency theCity);
	
	public void deleteById(Long theId);

}
