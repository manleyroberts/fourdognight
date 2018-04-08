//
//  UserVerificationModel.swift
//  Casa
//
//  Created by Isaac Weintraub on 2/19/18.
//  Copyright © 2018 Four Dog Night. All rights reserved.
//

class UserVerificationModel {
    private static var user_list: [String: AbstractUser] = DatabaseInterfacer.getUsers()
    
    static func attemptRegistration(name: String, username: String, password: String, isAdmin: Bool) -> Bool {
        if let _ = user_list[username] {
            return false
        } else if (isAdmin) {
            user_list[username] = Admin(name: name, username: username, password: password)
        } else {
            user_list[username] = User(name: name, username: username, password: password)
        }
        return true
    }
    
    static func attemptLogin(username: String, password: String) -> AbstractUser? {
        let user: AbstractUser? = user_list[username]
        if (user == nil || !user!.authenticate(password: password)) {
            return nil
        }
        return user
    }
}
