
// @GENERATOR:play-routes-compiler
// @SOURCE:/home/pooja/SOCProject1/project1/conf/routes
// @DATE:Sun Aug 27 20:09:11 EDT 2017


package router {
  object RoutesPrefix {
    private var _prefix: String = "/"
    def setPrefix(p: String): Unit = {
      _prefix = p
    }
    def prefix: String = _prefix
    val byNamePrefix: Function0[String] = { () => prefix }
  }
}
