package play.s.utils

import play.s.LJExtension
import today.opai.api.OpenAPI

/**
 * @author yuchenxue
 * @date 2025/03/05
 */

interface Accessor {
    val API: OpenAPI
        get() = LJExtension.API
}