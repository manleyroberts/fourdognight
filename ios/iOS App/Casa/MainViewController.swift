//
//  MainScreen.swift
//  Casa
//
//  Created by Isaac Weintraub on 2/22/18.
//  Copyright © 2018 Four Dog Night. All rights reserved.
//

import UIKit

class MainViewController: UIViewController {
    
    //MARK: Properties
    @IBOutlet weak var usernameLabel: UILabel!
    var nametext: String = ""
    
    //MARK: Actions
    @IBAction func logout(_ sender: Any) {
        let splashViewController: UIViewController = UIStoryboard(name: "SplashScreen", bundle: nil).instantiateViewController(withIdentifier: "splash")
        self.present(splashViewController, animated: true, completion: nil)
    }
    
    
    override func viewDidLoad() {
        super.viewDidLoad()
        // Do any additional setup after loading the view, typically from a nib.
    }

    override func viewDidAppear(_ animated: Bool) {
        super.viewDidAppear(animated)
        usernameLabel.text = nametext
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    func passUser(user: AbstractUser) {
        nametext = "\(user.name) | \(user.username) | " + ((user is Admin) ? "Admin" : "User")
    }
}
