import akka.actor.ActorLogging
import akka.cluster.pubsub.DistributedPubSub
import akka.cluster.pubsub.DistributedPubSubMediator.Subscribe
import akka.persistence._

import scala.collection.mutable

class ReadNodeActor extends PersistentActor with ClusterPackage with ActorLogging {

  override def persistenceId = "read"
  var state = mutable.Map("ping" -> "pong")

  //mediatorのsubscribeに設定
  val mediator = DistributedPubSub(context.system).mediator
  mediator ! Subscribe("read", self)

  　//PersistentActorのレシーブ
  override val receiveCommand: Receive = {
    case "Read" => state.toString()
    case Evt(f) => ""
    case other => log.info("Receive other event : " + other.toString())
  }
  　
  //リカバリー時のレシーブ、ノードのリカバリー時はここに呼ばれる
  override val receiveRecover: Receive = {
    case SnapshotOffer(_, snapshot: String) => {
      log.info("Read node recovery start")
    }
    case RecoveryCompleted  =>
      log.info("Read node recovery completed")
  }
}