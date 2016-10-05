import akka.actor.ActorLogging
import akka.cluster.pubsub.DistributedPubSub
import akka.cluster.pubsub.DistributedPubSubMediator.Publish
import akka.persistence._


/**
  * Created by takuya_st on 2016/10/03.
  */
class WriteNodeActor extends PersistentActor with ActorLogging {

  override def persistenceId = "write"
  var state = mutable.Map("ping" -> "pong")
  val mediator = DistributedPubSub(context.system).mediator

  //PersistentActorのレシーブ
  override val receiveCommand: Receive = {
    case Update(key, value) => update(key, value)
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

  def update(key: String, value: String) = {
    state += (key -> value)
  }
  def publishToRead(event: (A, B) => C) = {
    mediator ! Publish("read", Event(event))
  }
}