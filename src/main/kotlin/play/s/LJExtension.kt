package play.s

import today.opai.api.Extension
import today.opai.api.OpenAPI
import today.opai.api.annotations.ExtensionInfo

/**
 * @author yuchenxue
 * @date 2025/03/02
 */

@ExtensionInfo(name = "LJExtension", version = "1.0", author = "yuchenxue")
class LJExtension : Extension() {

    companion object {
        lateinit var API: OpenAPI
    }

    override fun initialize(openAPI: OpenAPI) {
        API = openAPI

        openAPI.registerFeature(ModuleHelper)
    }
}