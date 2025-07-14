package com.hokhanh.artist.util;

import java.util.List;
import java.util.function.Function;

import com.hokhanh.common.util.StringUtils;

public final class RepositoryUtils {
	public static <T, E> List<T> validateAndFetch(
		    List<Long> ids,
		    String otherNames,
		    Function<List<Long>, List<T>> repositoryFetcher,
		    List<E> errorHolder,
		    E error
		) {
		    boolean noOtherNames = otherNames == null || otherNames.isBlank() || StringUtils.cleanListString(otherNames) == null;
		    boolean noIds = ids == null || ids.isEmpty();

		    if (noOtherNames && noIds) {
		        errorHolder.add(error);
		        return null;
		    }
		    
		    if(!noIds) {
		    	List<T> results = repositoryFetcher.apply(ids);
		    	if(results.isEmpty() && noOtherNames) {
		    		errorHolder.add(error);
			        return null;
		    	}
		    	
		    	return results.isEmpty() ? null : results;
		    }
		    
		    return null;
	}
}
