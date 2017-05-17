package io.iohk.ethereum.network.handshaker

import akka.agent.Agent
import io.iohk.ethereum.db.storage.AppStateStorage
import io.iohk.ethereum.domain.Blockchain
import io.iohk.ethereum.network.ForkResolver
import io.iohk.ethereum.network.PeerManagerActor.PeerConfiguration
import io.iohk.ethereum.network.PeerActor.PeerInfo
import io.iohk.ethereum.utils.NodeStatus

case class EtcHandshaker private (handshakerState: HandshakerState[PeerInfo],
                                  handshakerConfiguration: EtcHandshakerConfiguration) extends Handshaker[PeerInfo] {

  protected def copy(handshakerState: HandshakerState[PeerInfo]): Handshaker[PeerInfo] = {
    EtcHandshaker(handshakerState, handshakerConfiguration)
  }

}

object EtcHandshaker {

  def apply(handshakerConfiguration: EtcHandshakerConfiguration): EtcHandshaker = {
    val initialState = HelloExchangeState(handshakerConfiguration)
    EtcHandshaker(initialState, handshakerConfiguration)
  }

}

trait EtcHandshakerConfiguration {
  val nodeStatusHolder: Agent[NodeStatus]
  val blockchain: Blockchain
  val appStateStorage: AppStateStorage
  val peerConfiguration: PeerConfiguration
  val forkResolverOpt: Option[ForkResolver]
}
