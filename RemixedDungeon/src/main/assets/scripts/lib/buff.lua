---
--- Generated by EmmyLua(https://github.com/EmmyLua)
--- Created by mike.
--- DateTime: 24.03.19 18:35
---

local buff = {}

buff.__index = buff

function buff.attachTo(self, buff, target)
    return true
end

function buff.detach(self, buff)
    return true
end

function buff.act(self, buff)
    return true
end

function buff.defaultDesc()
    return {
        image         = 0,
        name          = "custom buff",
        info          = "unconfigured custom buff",
    }
end

function buff.desc(self, buff)
    return buff.defaultDesc()
end

function buff.buffDesc(self)
    local ret = buff.defaultDesc()
    local own = self:desc()

    for k,v in pairs(ret) do
        ret[k] = own[k] or v
    end

    return ret
end


buff.init = function(desc)
    setmetatable(desc, buff)

    return desc
end

return buff