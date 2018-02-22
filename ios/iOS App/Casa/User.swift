//
//  User.swift
//  Casa
//
//  Created by Isaac Weintraub on 2/21/18.
//  Copyright © 2018 Four Dog Night. All rights reserved.
//

class User: AbstractUser {
    var name: String
    var username: String
    var password: String
    
    init(name: String, username: String, password: String) {
        self.name = name
        self.username = username
        self.password = password
    }
}
