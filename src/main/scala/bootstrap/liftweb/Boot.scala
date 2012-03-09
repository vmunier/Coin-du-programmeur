package bootstrap.liftweb

import net.liftweb._
import util._
import Helpers._

import common._
import http._
import sitemap._
import Loc._


/**
 * A class that's instantiated early and run.  It allows the application
 * to modify lift's environment
 */
class Boot {
  def boot {
    // where to search snippet
    LiftRules.addToPackages("code")

    // Build SiteMap
    val entries = List(
      Menu.i("Home") / "index", // the simple way to declare a menu
      Menu.i("Configurations") / "configurations" submenus(
        Menu.i("Bashrc") / "bashrc.configuration",
        Menu.i("Emacs") / "emacs.configuration",
        Menu.i("Eclipse") / "eclipse.configuration"),      
      Menu.i("Du code clair") / "regles.codage" submenus (
        Menu.i("Nommage") / "nommage",
	Menu.i("Les fonctions") / "fonctions",
	Menu.i("Les commentaires") / "commentaires",
	Menu.i("Traitement des erreurs") / "traitement.erreurs",
	Menu.i("Mise en forme") / "mise.en.forme"
      ), // bonnes pratiques 
      Menu.i("Langages") / "langages",
      Menu.i("Livres") / "livres",
      Menu.i("Outils") / "outils",
      Menu.i("Test") / "test",
      
      // more complex because this menu allows anything in the
      // /static path to be visible
      Menu(Loc("Static", Link(List("static"), true, "/static/index"), 
               "Static Content")))
    
    // set the sitemap.  Note if you don't want access control for
    // each page, just comment this line out.
    LiftRules.setSiteMap(SiteMap(entries:_*))

    //Show the spinny image when an Ajax call starts
    LiftRules.ajaxStart =
      Full(() => LiftRules.jsArtifacts.show("ajax-loader").cmd)
    
    // Make the spinny image go away when it ends
    LiftRules.ajaxEnd =
      Full(() => LiftRules.jsArtifacts.hide("ajax-loader").cmd)

    // Force the request to be UTF-8
    LiftRules.early.append(_.setCharacterEncoding("UTF-8"))

  }
}
