package com.hirw.facebookfriends.mr;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.hadoop.mapreduce.Reducer;

import com.hirw.facebookfriends.writables.Friend;
import com.hirw.facebookfriends.writables.FriendArray;
import com.hirw.facebookfriends.writables.FriendPair;

public class FacebookFriendsReducer extends 
	Reducer<FriendPair, FriendArray, FriendPair, FriendArray> {
	
	@Override
	public void reduce(FriendPair key, Iterable<FriendArray> values, Context context)
					throws IOException, InterruptedException {
		
		List<Friend[]> flist = new ArrayList<Friend[]>();
		List<Friend> commonFriendsList = new ArrayList<Friend>();
		int count = 0;
		
		for(FriendArray farray : values) {
			Friend[] f = Arrays.copyOf(farray.get(), farray.get().length, Friend[].class);
			flist.add(f);
			count++;
		}
		
		if(count != 2)
			return;
		
		for(Friend outerf : flist.get(0)) {
			for(Friend innerf : flist.get(1)) {
				if(outerf.equals(innerf))
					commonFriendsList.add(innerf);
			}
		}
		
	
		Friend[] commonFriendsArray = Arrays.copyOf(commonFriendsList.toArray(), commonFriendsList.toArray().length, Friend[].class);
		context.write(key, new FriendArray(Friend.class, commonFriendsArray));
	}


}
