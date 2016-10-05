package jp.appliv.genre.hierarchy.cluster

import akka.actor.{Actor, ActorRef}
import akka.cluster.client.{ClusterClients, ClusterClientUp, ClusterClientUnreachable, SubscribeClusterClients}


/**
  * Created by takuya_st on 2016/08/24.
  */
class ClusterServiceListener(targetReceptionist: ActorRef) extends Actor {

  override def preStart(): Unit = targetReceptionist ! SubscribeClusterClients

  override def receive: Receive = {
    def receiveWithClusterClients(clusterClients: Set[ActorRef]): Receive = {
      case ClusterClients(cs) =>
        context.become(receiveWithClusterClients(cs))
      case ClusterClientUp(c) =>
        context.become(receiveWithClusterClients(clusterClients + c))
      case ClusterClientUnreachable(c) =>
        context.become(receiveWithClusterClients(clusterClients - c))
    }
    receiveWithClusterClients(Set.empty)
  }
}
