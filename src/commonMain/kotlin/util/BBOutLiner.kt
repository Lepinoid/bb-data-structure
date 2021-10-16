package net.lepinoid.bbdatastructure.util

import kotlinx.serialization.Serializable
import net.lepinoid.bbdatastructure.serializer.BBOutLinerSerializer

@Serializable(with = BBOutLinerSerializer::class)
interface BBOutLiner