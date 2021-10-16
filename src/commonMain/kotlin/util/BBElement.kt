package net.lepinoid.bbdatastructure.util

import com.benasher44.uuid.Uuid

interface BBElement {
    var name: String
    var from: DoubleArray
    var isLocked: Boolean
    var uuid: Uuid
    var type: String?
}