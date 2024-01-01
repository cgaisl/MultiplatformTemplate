import SwiftUI

@main
struct iOSApp: App {
    
    @State var show: Bool = false
    
	var body: some Scene {
		WindowGroup {
            VStack {
                Button("switch") {
                    show = !show
                }
                
                if (show) {
                    ContentView()
                } else {
                    Text("Hello")
                }
            }
		}
	}
}
