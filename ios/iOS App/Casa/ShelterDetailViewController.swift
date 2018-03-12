//
//  ShelterDetailViewController.swift
//  Casa
//
//  Created by Jared Duncan on 3/12/18.
//  Copyright © 2018 Four Dog Night. All rights reserved.
//

import UIKit
import Foundation

class ShelterDetailViewController: UIViewController {
    weak var shelter: Shelter?
    @IBOutlet weak var shelterName: UILabel!
    @IBOutlet weak var capacity: UILabel!
    @IBOutlet weak var restrictions: UILabel!
    @IBOutlet weak var address: UILabel!
    @IBOutlet weak var phoneNumber: UILabel!
    @IBOutlet weak var notes: UILabel!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        shelterName.text = shelter!.name
        capacity.text = "Capacity: " + String(shelter!.capacity)
        restrictions.text = "Restrictions: " + shelter!.restrictionsString
        address.text = "Address: " + shelter!.location.address
        phoneNumber.text = "Phone Number: " + shelter!.phone
        notes.text = shelter!.notes
    }
}
