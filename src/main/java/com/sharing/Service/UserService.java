package com.sharing.Service;

import com.sharing.model.User;

public interface UserService {

    public User findUserById(Long userId) throws Exception;

    public User finduserByJwt(String jwt) throws Exception;

}
