//
//  Age.swift
//  Casa
//
//  Created by Jared Duncan on 3/12/18.
//  Copyright © 2018 Four Dog Night. All rights reserved.
//

import Foundation
enum Age: Int {
    case Families = 0
    case Children = 1
    case YoungAdults = 2
    case Adults = 3
    
    var description: String {
        switch rawValue {
        case 0:
            return "Families with Newborns"
        case 1:
            return "Children"
        case 2:
            return "Young Adults"
        case 3:
            return "Adults"
        default:
            return ""
        }
    }
    
    static var count: Int {
        return 4
    }
}
