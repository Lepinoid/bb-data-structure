package net.lepinoid.bbdatastructure.util

import kotlinx.serialization.Serializable
import net.lepinoid.bbdatastructure.serializer.ItemOutLinerSerializer

@Serializable(with = ItemOutLinerSerializer::class)
interface ItemOutLiner