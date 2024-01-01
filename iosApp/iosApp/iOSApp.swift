import SwiftUI
import Shared

@main
struct iOSApp: App {
    
    init() {
        doInitKoin()
    }
    
	var body: some Scene {
		WindowGroup {
            ContentView()
		}
	}
}
