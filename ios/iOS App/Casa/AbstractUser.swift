//
//  AbstractUser.swift
//  Casa
//
//  Created by Isaac Weintraub on 2/21/18.
//  Copyright © 2018 Four Dog Night. All rights reserved.
//

// Swift doesn't have abstract classes, REEEEEEEEE

protocol AbstractUser {
    var name: String {get set}
    var username: String {get}
    var password: String {get set}
}

extension AbstractUser {
    func authenticate(password: String) -> Bool {
        return self.password == password
    }
}

