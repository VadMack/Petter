package ru.gortea.petter.ui_kit.toolbar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.gortea.petter.ui_kit.R
import ru.gortea.petter.ui_kit.avatar.Avatar

@Composable
fun AvatarToolbar(
    text: String,
    avatar: Painter?,
    modifier: Modifier = Modifier,
    avatarPlaceholder: Painter = painterResource(R.drawable.ic_person_placeholder),
    profileClicked: (() -> Unit)? = null,
    avatarClicked: (() -> Unit)? = null,
    startIcon: @Composable (() -> Unit)? = null,
    endIcon: @Composable (() -> Unit)? = null
) {
    TopAppBar(
        modifier = modifier,
        backgroundColor = Color.Transparent,
        elevation = 0.dp,
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {

        if (startIcon != null) {
            startIcon()
            Spacer(modifier = Modifier.width(8.dp))
        }

        Row(
            modifier = Modifier.fillMaxSize()
                .padding(vertical = 4.dp)
                .clip(RoundedCornerShape(8.dp))
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = rememberRipple(),
                    enabled = profileClicked != null,
                    onClick = { profileClicked?.invoke() }
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clip(RoundedCornerShape(8.dp))
            ) {
                val avatarModifier = if (avatarClicked != null) {
                    Modifier.clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = rememberRipple(),
                        onClick = avatarClicked
                    )
                } else {
                    Modifier
                }

                Avatar(
                    image = avatar,
                    size = 28.dp,
                    placeholder = avatarPlaceholder,
                    modifier = avatarModifier
                )

                Text(
                    text = text,
                    style = MaterialTheme.typography.h3.copy(
                        color = MaterialTheme.colors.onBackground
                    )
                )
            }

            if (endIcon != null) {
                Spacer(modifier = Modifier.width(8.dp))
                endIcon()
            }
        }
    }
}
