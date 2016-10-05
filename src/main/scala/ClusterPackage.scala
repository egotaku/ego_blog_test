/**
  * Created by satoutakuya on 2016/10/05.
  */
trait ClusterPackage {
  case class Evt[A, B, C](f: (A, B) => C)
  case class Update(key: String, value: String)
}
